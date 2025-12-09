package Service_Test.Fine_Test;

import Entity.FineHistory;
import Entity.MediaItem;
import Entity.User;
import Enum.MediaItemType;
import Repository.FineHistoryRepository;
import Repository.UserRepository;
import Service.Fine.FineService;
import Validation.AmountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FineService_Test
{

    private UserRepository userRepository;
    private FineHistoryRepository fineHistoryRepository;
    private AmountValidator amountValidator;
    private FineService fineService;

    @BeforeEach
    void setUp()
    {
        userRepository = mock(UserRepository.class);
        fineHistoryRepository = mock(FineHistoryRepository.class);
        amountValidator = mock(AmountValidator.class);
        fineService = new FineService(userRepository, fineHistoryRepository, amountValidator);
    }

    @Test
    void calculateFine_returnsZero_whenItemIsNull()
    {
        BigDecimal result = fineService.calculateFine(null,5);
        assertEquals(BigDecimal.ZERO,result);
    }

    @Test
    void calculateFine_returnsZero_whenOverdueDaysNotPositive()
    {
        MediaItem item = mock(MediaItem.class);
        when(item.getMediaType()).thenReturn(MediaItemType.Book);
        BigDecimal result = fineService.calculateFine(item,0);
        assertEquals(BigDecimal.ZERO,result);
    }

    @Test
    void calculateFine_bookAndCd_positiveDays_returnsExpectedValues()
    {
        MediaItem book = mock(MediaItem.class);
        when(book.getMediaType()).thenReturn(MediaItemType.Book);
        BigDecimal bookFine = fineService.calculateFine(book,3);

        MediaItem cd = mock(MediaItem.class);
        when(cd.getMediaType()).thenReturn(MediaItemType.Cd);
        BigDecimal cdFine = fineService.calculateFine(cd,2);

        assertNotNull(bookFine);
        assertNotNull(cdFine);
        assertTrue(bookFine.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(cdFine.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void applyFine_zeroFine_doesNotTouchRepositories()
    {
        User user = mock(User.class);
        MediaItem item = mock(MediaItem.class);
        when(item.getMediaType()).thenReturn(MediaItemType.Book);

        fineService.applyFine(user,item,0);

        verifyNoInteractions(fineHistoryRepository,userRepository);
    }

    @Test
    void applyFine_positiveFine_updatesUserAndSavesHistory()
    {
        User user = mock(User.class);
        MediaItem item = mock(MediaItem.class);
        when(item.getMediaType()).thenReturn(MediaItemType.Book);

        fineService.applyFine(user,item,1);

        verify(user,times(1)).increaseFine(any(BigDecimal.class));
        verify(fineHistoryRepository,times(1)).save(any(FineHistory.class));
        verify(userRepository,times(1)).save(user);
    }

    @Test
    void payFine_throwsIllegalArgument_whenUserIsNull()
    {
        assertThrows(IllegalArgumentException.class,() -> fineService.payFine(null,"10"));
    }

    @Test
    void payFine_throwsIllegalArgument_whenParsedAmountIsNull()
    {
        User user = mock(User.class);
        when(amountValidator.validateAndParse("x")).thenReturn(null);
        assertThrows(IllegalArgumentException.class,() -> fineService.payFine(user,"x"));
    }

    @Test
    void payFine_throwsIllegalArgument_whenParsedAmountIsNotPositive()
    {
        User user = mock(User.class);
        when(amountValidator.validateAndParse("0")).thenReturn(BigDecimal.ZERO);
        assertThrows(IllegalArgumentException.class,() -> fineService.payFine(user,"0"));
    }

    @Test
    void payFine_throwsIllegalState_whenUserHasNoBalance()
    {
        User user = mock(User.class);
        when(user.getFineBalance()).thenReturn(BigDecimal.ZERO);
        when(amountValidator.validateAndParse("5")).thenReturn(BigDecimal.valueOf(5));
        assertThrows(IllegalStateException.class,() -> fineService.payFine(user,"5"));
    }

    @Test
    void payFine_throwsIllegalArgument_whenAmountGreaterThanBalance()
    {
        User user = mock(User.class);
        when(user.getFineBalance()).thenReturn(BigDecimal.valueOf(10));
        when(amountValidator.validateAndParse("20")).thenReturn(BigDecimal.valueOf(20));
        assertThrows(IllegalArgumentException.class,() -> fineService.payFine(user,"20"));
    }

    @Test
    void payFine_validAmount_decreasesFineAndSaves()
    {
        User user = mock(User.class);
        when(user.getFineBalance()).thenReturn(BigDecimal.valueOf(50));
        when(amountValidator.validateAndParse("20")).thenReturn(BigDecimal.valueOf(20));

        fineService.payFine(user,"20");

        verify(user,times(1)).decreaseFine(BigDecimal.valueOf(20));
        verify(userRepository,times(1)).save(user);
        verify(fineHistoryRepository,times(1)).save(any(FineHistory.class));
    }

    @Test
    void payFineInternal_doesNothing_whenInvalidArgs() throws Exception
    {
        Method m = FineService.class.getDeclaredMethod("payFineInternal",User.class,BigDecimal.class);
        m.setAccessible(true);

        User user = mock(User.class);

        m.invoke(fineService,user,BigDecimal.ZERO);
        m.invoke(fineService,user,null);
        m.invoke(fineService,null,BigDecimal.ONE);

        verify(user,never()).decreaseFine(any());
        verify(userRepository,never()).save(any());
        verify(fineHistoryRepository,never()).save(any());
    }

    @Test
    void payFineInternal_validArgs_decreasesFineAndSaves() throws Exception
    {
        Method m = FineService.class.getDeclaredMethod("payFineInternal",User.class,BigDecimal.class);
        m.setAccessible(true);

        User user = mock(User.class);
        BigDecimal amount = BigDecimal.valueOf(15);

        m.invoke(fineService,user,amount);

        verify(user,times(1)).decreaseFine(amount);
        verify(userRepository,times(1)).save(user);
        verify(fineHistoryRepository,times(1)).save(any(FineHistory.class));
    }
}

package Util.MessageFormater;

import Service.Book.OverdueBorrowDetection.OverdueBorrowedItemsData;

public class GeneralOverdueBorrowMessageFormater implements IMessageFormater<OverdueBorrowedItemsData>
{
    @Override
    public String formatMessage(OverdueBorrowedItemsData overdueBorrowedItemsData)
    {
        return overdueBorrowedItemsData.toString();
    }
}

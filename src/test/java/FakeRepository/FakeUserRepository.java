package FakeRepository;

import Entity.User;
import Repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FakeUserRepository implements UserRepository {
    private User storedUser;

    public void setStoredUser(User user) {
        this.storedUser = user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (storedUser != null && email != null && email.equals(storedUser.getEmail())) {
            return Optional.of(storedUser);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> findAllById(Iterable<Integer> integers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> S save(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public User getOne(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User getById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User getReferenceById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}

package com.example.personal_accounting.accounts_and_categories.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.personal_accounting.user_specific.UserNumber;

@Repository
public interface CategoryRepository extends ListCrudRepository<Category, Long> {
    public boolean existsByNameAndUserNumber(String name, UserNumber userNumber);

    public List<Category> findAllByCategoryTypeAndUserNumberOrderById(CategoryType categoryType, UserNumber userNumber);

    public Optional<Category> findByIdAndUserNumber(Long id, UserNumber userNumber);

    public void deleteByIdAndUserNumber(Long id, UserNumber userNumber);
}

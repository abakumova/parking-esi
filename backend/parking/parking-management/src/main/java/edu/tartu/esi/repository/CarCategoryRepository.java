package edu.tartu.esi.repository;

import edu.tartu.esi.model.CarCategory;
import org.springframework.data.repository.CrudRepository;

public interface CarCategoryRepository extends CrudRepository <CarCategory, String> {
}

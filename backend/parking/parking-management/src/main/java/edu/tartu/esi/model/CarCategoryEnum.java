package edu.tartu.esi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CarCategoryEnum {
    A("A", 1),
    B("B", 2),
    C("C", 3),
    D("D", 4);

    private String carCategoryName;
    private int carCategoryId;

    public static CarCategoryEnum getCarCategoryByName(String carCategoryName) {
        if (carCategoryName.equals(A.getCarCategoryName())) {
            return A;
        } else if (carCategoryName.equals(B.getCarCategoryName())) {
            return B;
        } else if (carCategoryName.equals(C.getCarCategoryName())) {
            return C;
        } else {
            return D;
        }
    }

    public static CarCategoryEnum getCarCategoryById(int carCategoryId) {
        return switch (carCategoryId) {
            case 1 -> A;
            case 2 -> B;
            case 3 -> C;
            case 4 -> D;
            default -> throw new IllegalStateException("Unexpected value: " + carCategoryId);
        };
    }
}

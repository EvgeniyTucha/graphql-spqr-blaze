package com.example.spqr.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortOption extends SortOptionInputField {

    private boolean ascending;

    @Override
    public void setSortOrder(SortOrder sortOrder) {
        super.setSortOrder(sortOrder);
        this.ascending = SortOrder.ASC.equals(sortOrder);
    }
}

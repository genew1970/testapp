package com.example.testapp;

import java.math.BigDecimal;

public enum ItemName {
         DIJON(0,"Dijon                            $2.99", new BigDecimal("2.99")),
    SPICYBROWN(1,"Spicy Brown               $2.43", new BigDecimal("2.43")),
        YELLOW(2,"Yellow                         $2.43", new BigDecimal("2.43")),
        FRENCH(3,"French                        $2.79", new BigDecimal("2.79")),
         HONEY(4,"Honey                         $3.77", new BigDecimal("3.77")),
    WHOLEGRAIN(5,"Whole Grain               $5.35", new BigDecimal("5.35")),
        GERMAN(6,"German                      $4.89", new BigDecimal("4.89")),
          BEER(7,"Beer                            $5.89", new BigDecimal("5.89")),
        CREOLE(8,"Creole                         $4.89", new BigDecimal("4.89")),
    GREYPOUPON(9,"Grey-Poupon              $5.78", new BigDecimal("5.78")),
     SRIRACHA(10,"Siracha                       $7.45", new BigDecimal("7.45"));

    public long id;
    public String itemName;
    public BigDecimal amount;

    ItemName(long id, String itemName, BigDecimal amount) {
        this.id = id;
        this.itemName = itemName;
        this.amount = amount;
    }

    public static BigDecimal fromId(long id) {
        ItemName[] itemNames = ItemName.values();
        for (ItemName itemName : itemNames) {
            if (itemName.id == (id)) {
                return itemName.amount;
            }
        }
        return new BigDecimal("1.00");
    }

    @Override
    public String toString() {
        return this.itemName;
    }
}

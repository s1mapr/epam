package com.my.utils;

import com.my.entities.Account;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sort {
    public static List<Account> sortByName(List<Account> accounts){
        return accounts.stream().sorted(Comparator.comparing(Account::getName)).collect(Collectors.toList());
    }
}

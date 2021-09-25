package com.mprog.springsecurityamigo.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Student {
    private final Integer id;
    private final String name;
}

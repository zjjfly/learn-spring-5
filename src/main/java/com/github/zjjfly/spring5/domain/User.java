package com.github.zjjfly.spring5.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021/2/19
 */
@Data
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String name;

    @Max(150)
    @Min(1)
    private Integer age;

    private Gender sex;

    private Date birthTime;

    @Transient
    private String xxx;

    @PrePersist
    void birthTime() {
        this.birthTime = new Date();
    }

    public enum Gender {
        /**
         * female
         */
        FEMALE,
        /**
         * male
         */
        MALE
    }
}


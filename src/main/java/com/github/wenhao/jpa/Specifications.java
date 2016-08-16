package com.github.wenhao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Range;
import org.springframework.data.jpa.domain.Specification;

import com.github.wenhao.jpa.specification.BetweenSpecification;
import com.github.wenhao.jpa.specification.EqualSpecification;
import com.github.wenhao.jpa.specification.GeSpecification;
import com.github.wenhao.jpa.specification.GtSpification;
import com.github.wenhao.jpa.specification.LeSpecification;
import com.github.wenhao.jpa.specification.LikeSpecification;
import com.github.wenhao.jpa.specification.LtSpecification;
import com.github.wenhao.jpa.specification.NotEqualSpecification;

public class Specifications<T> {
    private List<Specification<T>> specifications;

    public Specifications() {
        this.specifications = new ArrayList<>();
    }

    public Specifications<T> eq(String property, Object... values) {
        return eq(true, property, values);
    }

    public Specifications<T> eq(boolean condition, String property, Object... values) {
        if (condition) {
            this.specifications.add(new EqualSpecification<T>(property, values));
        }
        return this;
    }

    public Specifications<T> ne(String property, Object... object) {
        return ne(property, object, true);
    }

    public Specifications<T> ne(boolean condition, String property, Object... object) {
        if (condition) {
            this.specifications.add(new NotEqualSpecification<T>(property, object));
        }
        return this;
    }

    public Specifications<T> gt(String property, Number number) {
        return gt(true, property, number);
    }

    public Specifications<T> gt(boolean condition, String property, Number number) {
        if (condition) {
            this.specifications.add(new GtSpification<T>(property, number));
        }
        return this;
    }

    public Specifications<T> ge(String property, Number number) {
        return ge(true, property, number);
    }

    public Specifications<T> ge(boolean condition, String property, Number number) {
        if (condition) {
            this.specifications.add(new GeSpecification<T>(property, number));
        }
        return this;
    }

    public Specifications<T> lt(String property, Number number) {
        return lt(true, property, number);
    }

    public Specifications<T> lt(boolean condition, String property, Number number) {
        if (condition) {
            this.specifications.add(new LtSpecification<T>(property, number));
        }
        return this;
    }

    public Specifications<T> le(String property, Number number) {
        return le(true, property, number);
    }

    public Specifications<T> le(boolean condition, String property, Number number) {
        if (condition) {
            this.specifications.add(new LeSpecification<T>(property, number));
        }
        return this;
    }

    public Specifications<T> between(String property, Range<? extends Comparable<?>> range) {
        return between(true, property, range);
    }

    public Specifications<T> between(boolean condition, String property, Range<? extends Comparable<?>> range) {
        if (condition) {
            this.specifications.add(new BetweenSpecification<T>(property, range));
        }
        return this;
    }

    public Specifications<T> like(String property, String pattern) {
        return like(true, property, pattern);
    }

    public Specifications<T> like(boolean condition, String property, String pattern) {
        if (condition) {
            this.specifications.add(new LikeSpecification<T>(property, pattern));
        }
        return this;
    }

    public Specification<T> build() {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = this.specifications.stream()
                    .map(spec -> spec.toPredicate(root, query, criteriaBuilder))
                    .toArray(Predicate[]::new);
            return criteriaBuilder.and(predicates);
        };
    }
}

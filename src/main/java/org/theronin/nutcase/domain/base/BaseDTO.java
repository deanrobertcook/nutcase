package org.theronin.nutcase.domain.base;

import java.io.Serializable;
import java.util.Objects;

public abstract class BaseDTO implements Serializable {

    private Long id;

    public BaseDTO() {
    }

    public BaseDTO(BaseEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseDTO other = (BaseDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}

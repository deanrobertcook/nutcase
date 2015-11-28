package org.theronin.nutcase.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

public abstract class AuditBaseDTO extends BaseDTO {

    @NotNull
    @JsonIgnore
    private String createdBy;

    @NotNull
    @JsonIgnore
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @JsonIgnore
    private String lastModifiedBy;

    @JsonIgnore
    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();

    protected AuditBaseDTO() {
    }

    public AuditBaseDTO(BaseEntity entity) {
        super(entity);
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "AbstractAuditingEntity{" + "createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}

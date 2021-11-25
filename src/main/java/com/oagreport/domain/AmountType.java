package com.oagreport.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "AC_C_AMOUNT_TYPE")
public class AmountType extends BaseDto {
    @Id
    @ColumnDefault("0")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "CODE")
    private String code;


    @NotNull
    @Size(max = 250)
    @Column(name = "E_DESCRIPTION")
    private String edescription;

    @NotNull
    @Size(max = 500)
    @Column(name = "N_DESCRIPTION")
    private String ndescription;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    public AmountType() {
    }
}
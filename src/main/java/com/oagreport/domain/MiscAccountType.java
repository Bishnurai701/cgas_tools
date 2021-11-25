package com.oagreport.domain;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "AC_MISC_ACCOUNT_TYPE")
public class MiscAccountType extends BaseDto {
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

    @NotNull
    @Size(max = 1)
    @Column(name = "TYPE")
    private String type;

    @Column(name = "SORT_ORDER", nullable = false)
    private Integer sortOrder;

    @NotNull
    @Size(max = 1000)
    @Column(name = "REMARKS")
    private String remarks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_TYPE_ID")
    private List<MiscAccountHead> accountHeads;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    public MiscAccountType() {
    }
}

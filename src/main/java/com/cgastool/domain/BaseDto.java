package com.cgastool.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;

//@Data
@Setter
@Getter

@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)

//public abstract class BaseDto implements Serializable {
public abstract class BaseDto {
	@Column(name = "CREATED_BY", length = 50, nullable = false)
	private String createdBy;

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createdOn;

//    @Column(name = "UPDATED_BY", length = 50,nullable = false)
//    private String updatedBy;
//
//   // @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "UPDATED_ON", nullable = false)
//    @LastModifiedDate
//    private LocalDateTime updatedOn;

	@Column(name = "STATUS", nullable = false)
	private Integer status;

}

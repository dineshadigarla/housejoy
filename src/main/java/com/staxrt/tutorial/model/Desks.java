package com.staxrt.tutorial.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "desks")
@EntityListeners(AuditingEntityListener.class)
public class Desks {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "desk_name", nullable = false)
    private String deskName;

    @Column(name = "block_id")
    private Long blockId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;


    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;


  public long getId() {
        return id;
    }

  public void setId(long id) {
        this.id = id;
    }

  public String getDeskName() {
        return deskName;
    }

  public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

  public Long getBlockId() {
      return blockId;
  }

 public void setBlockId(Long blockId) {
      this.blockId = blockId;
  }

  public Date getCreatedAt() {
        return createdAt;
    }

  public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


  /**
   * Gets updated at.
   *
   * @return the updated at
   */
  public Date getUpdatedAt() {
        return updatedAt;
    }

  /**
   * Sets updated at.
   *
   * @param updatedAt the updated at
   */
  public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Desk{" +
                "id=" + id +
                ", deskName='" + deskName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }


}


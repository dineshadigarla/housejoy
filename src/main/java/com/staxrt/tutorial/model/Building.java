
package com.staxrt.tutorial.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "buildings")
@EntityListeners(AuditingEntityListener.class)
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "building_name", nullable = false)
    private String buildingName;

    @Column(name = "area", nullable = false)
    private String area;

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

  public String getBuildingName() {
        return buildingName;
    }

  public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

  public String getArea() {
        return area;
    }

  public void setArea(String area) {
        this.area = area;
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
        return "User{" +
                "id=" + id +
                ", buildingName='" + buildingName + '\'' +
                ", area='" + area + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }


}

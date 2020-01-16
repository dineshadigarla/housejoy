package com.example.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "buildings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Building {
	@Id
	@Column(name="id")
	private String id;
    @Column(name = "building_name")
    private String building_name;
    @Column(name = "area")
    private String area;
}

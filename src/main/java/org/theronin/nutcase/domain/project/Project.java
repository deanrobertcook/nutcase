package org.theronin.nutcase.domain.project;

import javax.persistence.*;

/**
 *
 */
@Entity
public class Project {

		@Id
		@GeneratedValue
		private Long id;

		@Column(unique = true)
		private String name;

		private String description;

		public Project() {
		}

		public Project(String name, String description) {
				this.name = name;
				this.description = description;
		}

		@Override
		public String toString() {
				return name;
		}

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public String getName() {
				return name;
		}

		public void setName(String name) {
				this.name = name;
		}

		public String getDescription() {
				return description;
		}

		public void setDescription(String description) {
				this.description = description;
		}
}

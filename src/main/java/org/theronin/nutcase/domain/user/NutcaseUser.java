package org.theronin.nutcase.domain.user;

import javax.persistence.*;

/**
 *
 */
@Entity
public class NutcaseUser {

		@Id
		@GeneratedValue
		private Long id;

		@Column(unique = true)
		private String email;
		private String name;
		private String password;

		protected NutcaseUser() {
		}

		public NutcaseUser(String email, String password) {
				this.email = email;
				this.password = password;
		}

		@Override
		public String toString() {
				if (name != null) {
						return name;
				} else {
						return email;
				}
		}

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public String getEmail() {
				return email;
		}

		public void setEmail(String email) {
				this.email = email;
		}

		public String getName() {
				return name;
		}

		public void setName(String name) {
				this.name = name;
		}

		public String getPassword() {
				return password;
		}

		public void setPassword(String password) {
				this.password = password;
		}
}

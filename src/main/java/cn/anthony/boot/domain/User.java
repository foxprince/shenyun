package cn.anthony.boot.domain;

public class User extends GenericNoSQLEntity {

    private static final long serialVersionUID = -6742282967978471611L;

    private String email;

    private String passwordHash;

    private Role role;

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPasswordHash() {
	return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
	this.passwordHash = passwordHash;
    }

    public Role getRole() {
	return role;
    }

    public void setRole(Role role) {
	this.role = role;
    }

}

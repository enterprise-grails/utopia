package com.utopia

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username blank: false, unique: true
		password blank: false, password: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		this.id ? UserRole.findAllByUser(this).collect { it.role } as Set : []
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	String toString() { username }
}

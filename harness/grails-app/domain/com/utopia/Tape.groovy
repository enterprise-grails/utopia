package com.utopia

class Tape {

	String name
	Date dateCreated

	static hasMany = [records:Record]

	static mapping = {
		records sort:'dateCreated', order:'asc'
	}

    static constraints = {
    }
}

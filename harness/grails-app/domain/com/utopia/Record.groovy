package com.utopia

class Record {

	RecordType type
	String message
	Date dateCreated

	static belongsTo = [tape:Tape]

    static constraints = {
    	message maxSize:2048
    }
}

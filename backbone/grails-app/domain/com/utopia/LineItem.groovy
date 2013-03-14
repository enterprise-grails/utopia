package com.utopia

class LineItem {

    Product product
    BigDecimal price
    Integer quantity
    
    Date dateCreated
    Date lastUpdated
    
    static belongsTo = [order:Order]
    
    static constraints = {
        price min:new BigDecimal("0.01")
        quantity min:1
    }
    
    String toString() {
        "${product.name} (${quantity})"
    }
}

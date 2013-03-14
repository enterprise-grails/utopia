package com.utopia

class Product {

    String sku
    String name
    String description
    BigDecimal price
    
    Date dateCreated
    Date lastUpdated

    static constraints = {
        sku blank:false, unique:true
        name blank:false
        description blank:false
        price min:new BigDecimal("0.01")
    }
    
    static mapping = {
        sku index:'idx_product_sku'
        description type:"text"
    }
    
    String toString() {
        "${name} (${sku})"
    }
}

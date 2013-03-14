import grails.util.Environment
import com.utopia.*

class BootStrap {

    def init = { servletContext ->
        
        def apiRole, apiAdminRole
        
        if (!Role.count()) {
            apiRole = new Role(authority:"ROLE_API_USER")
            apiRole.save(failOnError:true)
            
            apiAdminRole = new Role(authority:"ROLE_API_ADMIN")
            apiAdminRole.save(failOnError:true)
        }
        
        if (!User.count()) {
            def backbone = new User(username: "backbone", password:"asdf")
            backbone.save(failOnError:true)
            new UserRole(user:backbone, role: apiRole).save(failOnError:true)
            
            def router = new User(username: "router", password:"asdf")
            router.save(failOnError:true)
            new UserRole(user:router, role: apiRole).save(failOnError:true)
            
            def simpleMerch = new User(username: "simplemerch", password:"asdf")
            simpleMerch.save(failOnError:true)
            new UserRole(user:simpleMerch, role: apiRole).save(failOnError:true)
            
            def apiAdmin = new User(username: "admin", password:"asdf")
            apiAdmin.save(failOnError:true)
            new UserRole(user:apiAdmin, role: apiAdminRole).save(failOnError:true)
        }
        
        if (Environment.current != Environment.TEST) { // Don't want any data for unit tests
            def p1, p2
            
            if (!Product.count()) {
                p1 = new Product(name:"Widget", description:"The quintessential widget. Perfect for all your widget-oriented needs.", sku:"wdg-1", price:new BigDecimal("12.95"))
                p1.save(failOnError:true)
                p2 = new Product(name:"Super Widet", description:"The classic widget, modernized. The best widget money can buy.", sku:"wdg-2", price:new BigDecimal("19.95"))
                p2.save(failOnError:true)
            }
            
            if (!Order.count()) {
                def c = new Customer(firstName: "John", lastName: "Smith", street: "123 Any Street", city: "Any Town", state: "CT", zip: "01234", email: "jsmith@me.com")
                c.save(failOnError:true)
                def o = new Order(customer:c, cardNumber:"4111111111111111", expiration:"10/15")
                o.save(failOnError:true)
                def l1 = new LineItem(order:o, product:p1, quantity:1, price:new BigDecimal("12.95"))
                l1.save(failOnError:true)
                def l2 = new LineItem(order:o, product:p2, quantity:1, price:new BigDecimal("19.95"))
                l2.save(failOnError:true)
                
                def e = new LifeCycleEvent(order:o, eventName:"NewOrder", referenceId:"/order/1", published:new Date())
                e.save(failOnError:true)
            }
        }
    }
    def destroy = {
    }
}

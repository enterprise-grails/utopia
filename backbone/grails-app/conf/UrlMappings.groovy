class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		"/odie/$action?/$id?"(controller:"order")
		
        "/ocs/control/$id?"(controller:"ocs", action: "control")
        "/ocs/$id?"(resource:"ocs")
        
        "/ref/$path**"(resource:"ref")
        
        "/notification/$id?"(resource:"notification")
		
		"/"(view:"/index")
		"500"(view:'/error')
	}
}

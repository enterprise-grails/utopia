import com.utopia.*

class BootStrap {

	def httpListenerService 

    def init = { servletContext ->

		ServiceBridge.getInstance().setService(httpListenerService)    	

    	def httpProxy = new HttpProxy()
    	httpProxy.launch()

    }

    def destroy = {
    }
}

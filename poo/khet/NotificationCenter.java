package poo.khet;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Guarda las notificaciones que se envian, almacenandolas en una lista, 
 * para asi luego poder consultarlas desde otras clases.
 */
public final class NotificationCenter {
	//Capaz es mejor que sea una lista de Team así no hay q ir poniendo ifs para los Notification
	static LinkedList<Notification> notifications = new LinkedList<Notification>();
	
	public static void saveNotification(Notification notification) {
		notifications.add(notification);	
	}
	
	public static boolean isEmpty(){
		return notifications.isEmpty();
	}
	
	public static Notification getNotification(){
		if( notifications.isEmpty()){
			throw new NoSuchElementException();
		}
		return notifications.getLast(); //Mejor que agarre la última así no pasan cosas raras por el BeamManager de la AI
	}	
}

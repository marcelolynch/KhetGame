package poo.khet;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Guarda las notificaciones que se envian, almacenandolas en una lista, para asi luego poder consultarlas desde otras clases.
 *
 */
public final class NotificationCenter {
	
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
		return notifications.getFirst();
		
	}
	
	
	
}

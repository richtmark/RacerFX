
package application;

/**
 * Interface InterfaceControllScreen. Einsatz eines einfachen Interfaces. Das
 * Interface wird von all jenen Controllern implementiert die eine konkrete Ansicht steuern.
 * Das Interface ermoeglicht den wechsel der einzelnen Ansichten/Controller. 
 * 
 * @author Robert/Markus
 */
public interface InterfaceControllScreen {
	/**
	 * Methode zum setzen des Screens
	 * @param screenPage
	 */
    public void setScreenParent(MultiScreenController screenPage);
}

/**
 * Interface ControlledScreen. Einsatz eines einfachen Interfaces. Das
 * Interface wird von all jenen Klassen implementiert die eine GUI Ansicht repräsentieren.
 * Das Interface ermöglicht den Wechsel der einzelnen Ansichten.
 * 
 */
package application;

public interface ControlledScreen {
	/**
	 * Methode zum setzen des Screens
	 * @param screenPage
	 */
    public void setScreenParent(ScreensController screenPage);
}

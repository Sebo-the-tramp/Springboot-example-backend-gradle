package it.refill.backend.views;

/**
 * 
 * @version 1.0
 * 
 * View class is used to give interfaces that can be used to display or not some data during
 * different use-cases:
 * 
 * ex. --> supplier does not need to get back also the data about itself, but the data of the supplier may be needed
 *         by the admin that is looking or the customer which is interested in the supplier of some particular product
 * 
 * @since 1.0
 * 
 * @author Sebastian Cavada
 */

public class View {
    public interface Supplier {}
}
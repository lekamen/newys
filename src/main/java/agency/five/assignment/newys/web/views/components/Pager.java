package agency.five.assignment.newys.web.views.components;

import agency.five.assignment.newys.web.utils.MsgUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Pager extends HorizontalLayout {

    private Button previousButton;
    private Button nextButton;

    private int currentPage = 1;
    private int totalPages = 1;
    private int resultsPerPage;

    private PageChangeEventHandler pageChangeHandler;

    public Pager(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
        previousButton = new Button(MsgUtil.get("pager.previous.button"));
        previousButton.addClickListener(e -> previous());

        nextButton = new Button(MsgUtil.get("pager.next.button"));
        nextButton.addClickListener(e -> next());


        add(previousButton, nextButton);
    }

    private void previous() {
        currentPage = currentPage - 1;
        afterPageChange();
    }

    private void next() {
        currentPage = currentPage + 1;
        afterPageChange();
    }

    private void afterPageChange() {
        invokePageHandler(currentPage);
        enableButtons();
    }

    private void invokePageHandler(int newPage) {
        if (pageChangeHandler != null) {
            pageChangeHandler.onPageChange(newPage);
        }
    }

    public Pager onPageChange(PageChangeEventHandler pageChangeEventHandler) {
        this.pageChangeHandler = pageChangeEventHandler;
        return this;
    }

    private void enableButtons() {
        previousButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPages);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        enableButtons();
    }

    public void setTotalResults(long totalResults) {
        setTotalPages((int)Math.ceil((double)totalResults / resultsPerPage));
    }

    private void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        if (this.totalPages == 0) {
            // Ukupno stranica 0 znači da nema rezultata, ali nema smisla da se prikazuje
            // "stranica 1/0", pa se postavlja da je to ipak jedna stranica
            this.totalPages = 1;
        }
        enableButtons();
    }

    @FunctionalInterface
    public interface PageChangeEventHandler {
        void onPageChange(int page);
    }
}

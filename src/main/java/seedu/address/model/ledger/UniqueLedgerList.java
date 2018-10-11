package seedu.address.model.ledger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ledger.exceptions.LedgerNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of ledgers that enforces uniqueness between its elements and does not allow nulls.
 * A ledger is considered unique by comparing using {@code Ledger#isSameLedger(Ledger)}. As such, adding and updating of
 * ledger uses Ledger#isSameLedger(Ledger) for equality so as to ensure that the ledger being added or updated is
 * unique in terms of identity in the UniqueLedgerList. However, the removal of a person uses Ledger#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniqueLedgerList implements Iterable<Ledger> {

    private final ObservableList<Ledger> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Ledger toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameLedger);
    }

    /**
     * Adds a ledger to the list.
     * If the ledger date is found in the list, the amount will be added or deducted.
     */
    public void add(Ledger toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            //do credit or debit;
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setDate(Ledger target, Ledger editedDate) {
        requireAllNonNull(target, editedDate);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LedgerNotFoundException();
        }

        if (contains(editedDate)) {
            //add or deduct balance from ledger;
        }

        internalList.set(index, editedDate);
    }

    /**
     * Removes the equivalent ledger from the list.
     * The ledger must exist in the list.
     */
    public void remove(Ledger toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LedgerNotFoundException();
        }
    }

    public void setLedgers(UniqueLedgerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setLedgers(List<Ledger> ledgers) {
        requireAllNonNull(ledgers);
        if (!ledgersAreUnique(ledgers)) {
            //add or deduct duplicate balance;
        }

        internalList.setAll(ledgers);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Ledger> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Ledger> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLedgerList // instanceof handles nulls
                        && internalList.equals(((UniqueLedgerList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean ledgersAreUnique(List<Ledger> ledgers) {
        for (int i = 0; i < ledgers.size() - 1; i++) {
            for (int j = i + 1; j < ledgers.size(); j++) {
                if (ledgers.get(i).isSameLedger(ledgers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

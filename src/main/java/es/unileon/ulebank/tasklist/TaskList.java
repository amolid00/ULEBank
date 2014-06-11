/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.tasklist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.time.Time;

/**
 *
 * @author runix
 */
public class TaskList {

    /**
     * Tasks to execute.
     */
    private final List<Task> tasks;

    /**
     * Tasks executed.
     */
    private final List<Task> tasksDone;

    /**
     * Deleted tasks before execute it.
     */
    private final List<Task> deletedTasks;

    /**
     * Tasks's comparator to sort by effective date.
     */
    private final TaskDateComparator comparator;

    /**
     * Bank's time.
     */
    private final Time time;

    /**
     * Object instance
     */
    private static TaskList instance;

    /**
     * Create a new tasklist.
     */
    private TaskList() {
        this.tasks = new ArrayList<Task>();
        this.tasksDone = new ArrayList<Task>();
        this.deletedTasks = new ArrayList<Task>();
        this.comparator = new TaskDateComparator();
        this.time = Time.getInstance();
    }

    /**
     * Get a instance
     * 
     * @return
     */
    public static TaskList getInstance() {
        if (TaskList.instance == null) {
            TaskList.instance = new TaskList();
        }
        return TaskList.instance;
    }

    /**
     *
     * @param task
     * @return
     */
    public synchronized boolean addTask(Task task) {
        boolean add = true;
        if (task == null) {
            add = false;
        } else {
            add = !this.isInAnyList(task.getID());
        }
        if (add) {
            this.tasks.add(task);
            this.sort();
        }
        return add;
    }

    /**
     * 
     * @param task
     * @return
     */
    public synchronized boolean addDoneTask(Task task) {
        boolean add = true;
        this.time.updateTime();
        if ((task == null)
                || (task.getEffectiveDate().getTime() > this.time.getTime())) {
            add = false;
        } else {
            add = !this.isInAnyList(task.getID());
        }

        if (add) {
            this.tasksDone.add(task);
            this.sort();
        }
        return add;
    }

    /**
     *
     * @param task
     * @return
     */
    public boolean moveTaskToTrash(Handler id) {
        boolean delete = false;
        if (id != null) {
            int i = -1;
            while ((++i < this.tasks.size()) && !delete) {
                if (id.compareTo(this.tasks.get(i).getID()) == 0) {
                    final Task c = this.tasks.get(i);
                    this.tasks.remove(i);
                    this.deletedTasks.add(c);
                    delete = true;
                }
            }
            this.sort();
        }
        return delete;
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    public boolean undoTask(Handler id) throws Exception {
        int i = -1;
        boolean done = false;
        while ((++i < this.tasksDone.size()) && !done) {
            final Task c = this.tasksDone.get(i);
            if (c.getID().compareTo(id) == 0) {
                c.undo();
                done = true;
            }
        }
        return done;
    }

    /**
     * @throws Exception
     *
     */
    public synchronized void executeTasks() throws Exception {
        while (!this.tasks.isEmpty()
                && (this.tasks.get(0).getEffectiveDate().getTime() <= this.time
                        .getTime())) {
            final Task c = this.tasks.get(0);
            c.execute();
            this.tasks.remove(0);
            this.tasksDone.add(c);
        }
        this.sort();
    }

    private boolean isInAnyList(Handler id) {
        return this.isInDeleteTasks(id) || this.isInTasks(id)
                || this.isInTasksDone(id);
    }

    private boolean isInDeleteTasks(Handler id) {
        boolean found = false;
        int i = -1;
        while ((++i < this.deletedTasks.size()) && !found) {
            if (id.compareTo(this.deletedTasks.get(i).getID()) == 0) {
                found = true;
            }
        }
        return found;
    }

    private boolean isInTasks(Handler id) {
        boolean found = false;
        int i = -1;
        while ((++i < this.tasks.size()) && !found) {
            if (id.compareTo(this.tasks.get(i).getID()) == 0) {
                found = true;
            }
        }
        return found;
    }

    private boolean isInTasksDone(Handler id) {
        boolean found = false;
        int i = -1;
        while ((++i < this.tasksDone.size()) && !found) {
            if (id.compareTo(this.tasksDone.get(i).getID()) == 0) {
                found = true;
            }
        }
        return found;
    }

    private synchronized void sort() {
        Collections.sort(this.tasks, this.comparator);
        Collections.sort(this.tasksDone, this.comparator);
    }

    /**
     *
     * @return
     */
    public List<Task> getDeteledTasks() {
        return new ArrayList<Task>(this.deletedTasks);
    }

    /**
     *
     * @return
     */
    public List<Task> getTaskList() {
        return new ArrayList<Task>(this.tasks);
    }

    /**
     *
     * @return
     */
    public List<Task> getTasksListDone() {
        return new ArrayList<Task>(this.tasksDone);
    }

}

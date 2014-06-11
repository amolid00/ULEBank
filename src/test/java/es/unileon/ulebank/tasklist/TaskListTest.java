package es.unileon.ulebank.tasklist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.time.Time;

public class TaskListTest {

    private List<MockTask> taskToDo;
    private List<MockTask> taskDone;
    private TaskList taskList;
    private static boolean init = false;

    public TaskListTest() {
        if (!TaskListTest.init) {
            this.taskToDo = new ArrayList<MockTask>();
            this.taskDone = new ArrayList<MockTask>();
            Time.getInstance().updateTime();
            this.taskList = TaskList.getInstance();
            for (int i = 0; i < 10; ++i) {
                final MockTask c = this.getTask(new Date((100 * i) + 120));
                this.taskToDo.add(c);
                Assert.assertTrue(this.taskList.addTask(c));
            }

            for (int i = 0; i < 10; ++i) {
                final MockTask c = this.getTask(new Date(i));
                this.taskDone.add(c);
                Assert.assertTrue(this.taskList.addDoneTask(c));
            }
            TaskListTest.init = true;
        }

    }

    // Doing almost all tests with a one method because
    // this class is unique (singleton pattern) and the state cannot be reset
    // so, if something is change in one test it might affect
    // the others
    @Test
    public void testTaskList() throws Exception {
        final List<Task> tasks = new ArrayList<Task>();
        tasks.addAll(this.taskDone);
        tasks.addAll(this.taskToDo);
        Collections.sort(tasks, new TaskDateComparator());
        Iterator<Task> it = tasks.iterator();
        this.taskList.executeTasks();
        Iterator<Task> fromTaskList = this.taskList.getTasksListDone()
                .iterator();
        while (it.hasNext() && fromTaskList.hasNext()) {
            Assert.assertEquals(it.next(), fromTaskList.next());
        }
        Assert.assertFalse(this.taskList.moveTaskToTrash(new GenericHandler(
                "123123aasdad")));
        for (int i = 0; i < this.taskToDo.size(); ++i) {
            Assert.assertEquals(this.taskToDo.get(i).getState(),
                    MockTask.STATE_EXECUTE);
        }
        Assert.assertEquals(it.hasNext(), fromTaskList.hasNext());

        Assert.assertFalse(this.taskList.addDoneTask(this.getTask(new Date(Time
                .getInstance().getTime() + Time.DAYS_TO_MILLIS))));
        it = tasks.iterator();
        fromTaskList = this.taskList.getTasksListDone().iterator();
        while (it.hasNext() && fromTaskList.hasNext()) {
            Assert.assertEquals(it.next(), fromTaskList.next());
        }
        Assert.assertEquals(it.hasNext(), fromTaskList.hasNext());

        Task c = this.getTask(new Date(System.currentTimeMillis()));
        Assert.assertTrue(this.taskList.addTask(c));
        Assert.assertFalse(this.taskList.addTask(c));
        Assert.assertFalse(this.taskList.addDoneTask(c));

        Assert.assertTrue(this.taskList.moveTaskToTrash(c.getID()));

        it = tasks.iterator();
        fromTaskList = this.taskList.getTasksListDone().iterator();
        while (it.hasNext() && fromTaskList.hasNext()) {
            Assert.assertEquals(it.next(), fromTaskList.next());
        }
        Assert.assertEquals(it.hasNext(), fromTaskList.hasNext());

        Assert.assertFalse(this.taskList.moveTaskToTrash(new GenericHandler(
                "notFound..")));

        it = tasks.iterator();
        fromTaskList = this.taskList.getTasksListDone().iterator();
        while (it.hasNext() && fromTaskList.hasNext()) {
            Assert.assertEquals(it.next(), fromTaskList.next());
        }
        Assert.assertEquals(it.hasNext(), fromTaskList.hasNext());

        Task c1 = this.getTask(new Date(System.currentTimeMillis() - 3));
        Assert.assertTrue(this.taskList.addTask(c1));
        Assert.assertFalse(this.taskList.moveTaskToTrash(new GenericHandler(
                "notFound..")));
        it = tasks.iterator();
        fromTaskList = this.taskList.getTasksListDone().iterator();
        while (it.hasNext() && fromTaskList.hasNext()) {
            Assert.assertEquals(it.next(), fromTaskList.next());
        }
        Assert.assertEquals(it.hasNext(), fromTaskList.hasNext());

        Assert.assertTrue(this.taskList.moveTaskToTrash(c1.getID()));
        final Iterator<Task> deleteIt = this.taskList.getDeteledTasks()
                .iterator();
        Assert.assertEquals(c, deleteIt.next());
        Assert.assertEquals(c1, deleteIt.next());
        Assert.assertEquals(deleteIt.hasNext(), false);

        c = this.getTask(new Date(10 * 10 * 10));
        c1 = this.getTask(new Date(10 * 10 * 11));
        this.taskList.executeTasks();
        Assert.assertTrue(this.taskList.addTask(c));
        Assert.assertTrue(this.taskList.addTask(c1));
        Assert.assertFalse(this.taskList.addTask(c));
        Assert.assertFalse(this.taskList.addTask(c1));
        final Iterator<Task> task = this.taskList.getTaskList().iterator();
        Assert.assertEquals(task.next(), c);
        Assert.assertEquals(task.next(), c1);
        Assert.assertFalse(task.hasNext());

        Assert.assertTrue(this.taskList.undoTask(this.taskToDo.get(1).getID()));
        Assert.assertEquals(this.taskToDo.get(1).getState(),
                MockTask.STATE_UNDO);

        Assert.assertFalse(this.taskList
                .undoTask(new GenericHandler("invalid")));

        Assert.assertFalse(this.taskList.addTask(null));
        Assert.assertFalse(this.taskList.addDoneTask(null));
        Assert.assertFalse(this.taskList.moveTaskToTrash(null));

        c = this.getTask(new Date(System.currentTimeMillis() + 10));
        c1 = this.getTask(new Date(System.currentTimeMillis() + 9));
        Assert.assertTrue(this.taskList.addTask(c));
        Assert.assertTrue(this.taskList.addTask(c1));
        Assert.assertTrue(this.taskList.moveTaskToTrash(c.getID()));
        Time.getInstance().updateTime();
        this.taskList.executeTasks();
        final Iterator<Task> todo = this.taskList.getTaskList().iterator();
        Assert.assertEquals(todo.next(), c1);
        Time.getInstance().setTime(10000);
        Assert.assertFalse(this.taskList.addTask(c));
        c = this.getTask(new Date(9999));
        Assert.assertTrue(this.taskList.addDoneTask(c));
        Assert.assertFalse(this.taskList.addDoneTask(c));
    }

    private MockTask getTask(Date date) {
        return new MockTask(date, new GenericHandler(String.valueOf(date
                .getTime())));
    }
}

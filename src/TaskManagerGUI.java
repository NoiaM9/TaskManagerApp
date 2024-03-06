import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TaskManagerGUI extends JFrame {
    private TaskManager taskManager;
    private DefaultListModel<String> taskListModel;

    public TaskManagerGUI() {
        this.taskManager = new TaskManager();
        this.taskListModel = new DefaultListModel<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Task Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Image icon = Toolkit.getDefaultToolkit().getImage("Images/TaskLogo.png");
        setIconImage(icon);

        createComponents();

        pack();
        setVisible(true);
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JList<String> taskList = new JList<>(taskListModel);
        mainPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JTextField taskTextField = new JTextField();
        JButton addButton = new JButton("Add Task");
        JButton completeButton = new JButton("Mark as Completed");

        addButton.addActionListener(e -> {
            String taskDescription = taskTextField.getText();
            if (!taskDescription.isEmpty()) {
                taskManager.addTask(taskDescription);
                updateTaskList();
                taskTextField.setText("");
            }
        });

        completeButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                taskManager.markTaskAsCompleted(selectedIndex);
                updateTaskList();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(taskTextField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        inputPanel.add(completeButton, BorderLayout.SOUTH);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateTaskList() {
        taskListModel.clear();
        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskListModel.addElement(task.toString());
        }
    }
}

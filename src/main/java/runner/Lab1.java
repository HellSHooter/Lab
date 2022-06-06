package runner;

import entity.Group;
import entity.Student;
import utils.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

//Выборка
//1. Для каждой группы вывести количество отчисленных студентов.
//2. Вывести сведения о студентах, обучающихся более 4 лет.

public class Lab1 {

    public static void main(String[] args) {
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        //1.
        List<Group> Group = session.createQuery("FROM Group").list();
        int studs = 0;
        for (Group group : Group) {
            studs += group.getStudents().stream().filter(student -> student.getStatus().equals("Отчислен")).toList().size();
            System.out.println("В группе " + group.getName() + " отчисленно " + studs + " человек.");
            studs = 0;
        }

        //2.
        List<Student> students = session.createQuery("FROM Student").list();
        Date curDate = new Date();
        for (Student student : students) {
            if (curDate.getYear() - student.getGroup().getCreateDate().getYear() > 4)
                System.out.println(student);
        }

        session.flush();
        transaction.commit();
        session.close();
        sf.close();
    }
}

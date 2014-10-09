/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nonserver;

import NonsuchInterface.NonsuchRemote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import nonsuchdata.*;

/**
 *
 * @author Administrator this is the implement all the data methods from nonsuch
 * remote will be coded in here.
 */
//linking with RMI rn-time library involves use of class nicastRemoteObject.
public class RMINonsuchServer extends UnicastRemoteObject implements NonsuchRemote {

    private EntityManagerFactory emf;
    private EntityManager em;

    public RMINonsuchServer() throws RemoteException {
        try {
            emf = Persistence.createEntityManagerFactory("NonserverPU");
            em = emf.createEntityManager();

            // put code here which is already mentioned in nonsuchremote.
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }

    }
// get staff information just for log in 
//@parameter staffid;
//@return Staff  or  null if not find.

    @Override
    public Staff staffInfo(String id) throws RemoteException {

        Query q = em.createNamedQuery("Staff.findByStaffid");
        q.setParameter("staffid", id);
        List<Staff> staff = (List<Staff>) q.getResultList();
        if (staff.isEmpty()) {
            em.clear();
            return null;
        } else {
            for (Staff f : staff) {
                em.clear();
                return f;

            }
            return null;
        }


    }

    @Override
    public Subject subjectInfo(String id) throws RemoteException {
        Query q = em.createNamedQuery("Subject.findBySubjectid");
        q.setParameter("subjectid", id);
        List<Subject> subject = (List<Subject>) q.getResultList();
        for (Subject s1 : subject) {
            return s1;
        }
        em.clear();
        return null;
    }

    @Override
    public int getTask(String taskid, String subjectid) throws RemoteException {
        int mark = 0;
        Query query = em.createQuery("SELECT t FROM Tasks t WHERE t.tasksPK.subjectid = :subjectid AND t.tasksPK.taskid = :taskid");
        query.setParameter("subjectid", subjectid);
        query.setParameter("taskid", taskid);
        List<Tasks> task = query.getResultList();
        for (Tasks t : task) {
            mark = t.getMark();
        }
        return mark;
    }

    @Override
    public void insertMark(String subjectId, String taskid, String studentId, int mark) throws RemoteException {
        Marks aMark = new Marks();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        aMark.setMark(mark);
        aMark.setStudentid(studentId);
        aMark.setTaskid(taskid);
        aMark.setSubjectid(subjectId);
        em.persist(aMark);
        em.getTransaction().commit();
        em.clear();



    }

    @Override
    public int searchMark(String studentId, String subjectId, String taskId) throws RemoteException {
        int mark = 0;
        Query query = em.createQuery("SELECT m FROM Marks m WHERE m.subjectid = :subjectid AND m.taskid = :taskid AND m.studentid = :studentid");
        query.setParameter("subjectid", subjectId);
        query.setParameter("taskid", taskId);
        query.setParameter("studentid", studentId);
        List<Marks> marks = query.getResultList();
        if (marks.isEmpty()) {
            return 101;
        }
        for (Marks m : marks) {
            mark = m.getMark();;
        }
        return mark;


    }

    @Override
    public void deleteTask(String taskid) throws RemoteException {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        Query query = em.createNamedQuery("Tasks.findByTaskid");
        query.setParameter("taskid", taskid);
        List<Tasks> list = query.getResultList();
        if (!list.isEmpty()) {
            for (Tasks t : list) {
                em.remove(t);
            }
            em.getTransaction().commit();
            em.clear();
        }
        Query query1 = em.createNamedQuery("Marks.findByTaskid");
        query1.setParameter("taskid", taskid);

        List<Marks> marks = query1.getResultList();
        if (!marks.isEmpty()) {
            for (Marks m : marks) {
                em.remove(m);
            }
            em.getTransaction().commit();
            em.clear();
        }

    }

    @Override
    public void deleteEnrolment(String studentId, String subjectId) throws RemoteException {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        Query query = em.createQuery("SELECT e FROM Enrolment e WHERE e.enrolmentPK.studentid = :studentid AND e.enrolmentPK.subjectid = :subjectid");
        query.setParameter("subjectid", subjectId);
        query.setParameter("studentid", studentId);
        List<Enrolment> list = query.getResultList();

        for (Enrolment e : list) {
            em.remove(e);
        }
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public boolean enrolmented(String studentId, String subjectId) throws RemoteException {
        boolean flag = false;
        Query q = em.createNamedQuery("Enrolment.findBySubjectid");
        q.setParameter("subjectid", subjectId);
        List<Enrolment> enrolment = (List<Enrolment>) q.getResultList();
        int num = enrolment.size();
        if (num != 0) {
            for (Enrolment e : enrolment) {
                if (e.getEnrolmentPK().getStudentid().equals(studentId)) {
                    flag = true;
                }
            }
        }

        return flag;

    }

    @Override
    public String[] enrolmentInfo(String subjectId) throws RemoteException {

        Query q = em.createNamedQuery("Enrolment.findBySubjectid");
        q.setParameter("subjectid", subjectId);
        List<Enrolment> enrolment = (List<Enrolment>) q.getResultList();
        int num = enrolment.size();
        int i = 0;
        if (num == 0) {
            em.clear();
            return null;
        }
        String[] studentId = new String[num];
        for (Enrolment e : enrolment) {
            studentId[i] = e.getEnrolmentPK().getStudentid().toString();
            i++;
        }
        em.clear();
        return studentId;
    }

    @Override
    public Collection<Subject> getSubjects(String id) throws RemoteException {
        Staff staff = em.find(Staff.class, id);
        Collection<Subject> results = staff.getSubjectCollection();
        return results;
    }

    //get tasks for assessemnt table;
    @Override
    public Tasks[] getTasks(String id) throws RemoteException {
        Query q = em.createNamedQuery("Tasks.findBySubjectid");
        q.setParameter("subjectid", id);
        List<Tasks> tasks = (List<Tasks>) q.getResultList();
        Tasks[] t = new Tasks[tasks.size()];
        tasks.toArray(t);
        em.clear();
        return t;
    }

    @Override
    public void setContent(String content, String objectives, String subjectid) throws RemoteException {
        //set description of one subject.   
        Subject subject = em.find(Subject.class, subjectid);
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        subject.setContent(content);
        subject.setObjectives(objectives);
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public void updateMark(String taskid, String studentid, String subjectid, int mark) throws RemoteException {

        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        Query query = em.createQuery("SELECT m FROM Marks m WHERE m.studentid = :studentid  AND m.taskid = :taskid");
        query.setParameter("studentid", studentid);
        query.setParameter("taskid", taskid);
        List<Marks> marks = query.getResultList();
        if (!marks.isEmpty()) {
            System.out.println(marks.size());
            for (Marks m : marks) {
                System.out.println(m.getTaskid());
                m.setMark(mark);
            }

        } else {
            Marks mNew = new Marks();
            mNew.setMark(mark);
            mNew.setStudentid(studentid);
            mNew.setSubjectid(subjectid);
            mNew.setTaskid(taskid);
            em.persist(mNew);
        }
        em.getTransaction().commit();
        em.clear();

    }

    @Override
    public void deleteAlltasks(String subjectId) throws RemoteException {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        Query q = em.createNamedQuery("Tasks.findBySubjectid");
        q.setParameter("subjectid", subjectId);
        List<Tasks> tasks = (List<Tasks>) q.getResultList();
        Tasks[] t = new Tasks[tasks.size()];
        tasks.toArray(t);
        for (int j = 0; j < tasks.size(); j++) {
            em.remove(t[j]);
        }
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public void addEnrolment(String updated, String subjectId) throws RemoteException {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        Enrolment e = new Enrolment();
        EnrolmentPK ePK = new EnrolmentPK();
        ePK.setStudentid(updated);
        ePK.setSubjectid(subjectId);
        e.setEnrolmentPK(ePK);
        em.persist(e);
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public void updateTasks(String[][] tasks, String subjectId) throws RemoteException {

        int num = tasks.length;
        for (int i = 0; i < num; i++) {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            Tasks t = new Tasks();
            TasksPK pk = new TasksPK();
            pk.setSubjectid(subjectId);
            pk.setTaskid(tasks[i][0]);
            t.setTasksPK(pk);
            int mark = Integer.parseInt(tasks[i][1]);
            t.setMark(mark);
            t.setDescription(tasks[i][2]);
            em.persist(t);
            em.getTransaction().commit();
            em.clear();
        }

    }
}

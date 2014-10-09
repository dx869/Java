/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NonsuchInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import nonsuchdata.*;

/**
 *
 * @author Administrator
 */
public interface NonsuchRemote extends Remote {
//interface , put methods here and put code into Implement (RMINonsuchServer) later.

    public Staff staffInfo(String id) throws RemoteException;

    public Collection<Subject> getSubjects(String id) throws RemoteException;

    public void setContent(String content, String objectives, String subjectid) throws RemoteException;

    public Subject subjectInfo(String id) throws RemoteException;

    public Tasks[] getTasks(String id) throws RemoteException;

    public void deleteAlltasks(String subjectId) throws RemoteException;

    public void updateTasks(String[][] tasks, String subjectId) throws RemoteException;

    public String[] enrolmentInfo(String subjectId) throws RemoteException;

    public void deleteEnrolment(String studentId, String subjectId) throws RemoteException;

    public void addEnrolment(String update, String subjectId) throws RemoteException;

    public boolean enrolmented(String studentId, String subjectId) throws RemoteException;

    public int getTask(String taskid, String subjectid) throws RemoteException;

    public void insertMark(String subjectId, String taskid, String studentId, int mark) throws RemoteException;

    public int searchMark(String studentId, String subjectId, String taskId) throws RemoteException;
    public void deleteTask(String taskid)throws RemoteException;
    public void updateMark(String taskid,String studentid,String subjectid,int mark)throws RemoteException;
}

package Aphorism;


/**
* Aphorism/AdminPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./idlsrc/Aphorism.idl
* Thursday, September 19, 2013 6:25:01 PM PDT
*/

public abstract class AdminPOA extends org.omg.PortableServer.Servant
 implements Aphorism.AdminOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("createPlayer", new java.lang.Integer (0));
    _methods.put ("changePassword", new java.lang.Integer (1));
    _methods.put ("deletePlayer", new java.lang.Integer (2));
    _methods.put ("shutdownServer", new java.lang.Integer (3));
    _methods.put ("getnames", new java.lang.Integer (4));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // Aphorism/Admin/createPlayer
       {
         String name = in.read_string ();
         String password = in.read_string ();
         boolean $result = false;
         $result = this.createPlayer (name, password);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // Aphorism/Admin/changePassword
       {
         String name = in.read_string ();
         String password = in.read_string ();
         boolean $result = false;
         $result = this.changePassword (name, password);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // Aphorism/Admin/deletePlayer
       {
         String name = in.read_string ();
         boolean $result = false;
         $result = this.deletePlayer (name);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 3:  // Aphorism/Admin/shutdownServer
       {
         this.shutdownServer ();
         out = $rh.createReply();
         break;
       }

       case 4:  // Aphorism/Admin/getnames
       {
         String $result[] = null;
         $result = this.getnames ();
         out = $rh.createReply();
         Aphorism.stringlistHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Aphorism/Admin:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Admin _this() 
  {
    return AdminHelper.narrow(
    super._this_object());
  }

  public Admin _this(org.omg.CORBA.ORB orb) 
  {
    return AdminHelper.narrow(
    super._this_object(orb));
  }


} // class AdminPOA

package com.webservice.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ReportPublisherException extends RuntimeException
{
  final static String REVISION_INFO =
    "Copyright Riverwood Solutions Inc, $Project$ $Workfile$ $Revision$ $Date$ $Author$";

  /**
   * Constructs a ChainedUncheckedException without a message or chained exception
   */
  public ReportPublisherException()
  {
    super();
  }
  
  /**
   * Constructors a ChainedUncheckedException with only a message
   * @param msg the message to include
   */
  public ReportPublisherException(String msg)
  {
    super(msg);
  }

  /** Constructors a ChainedUncheckedException with a message and chained exception
   *  @param msg the message
   *  @param exception the exception to wrap
   */
  public ReportPublisherException(String msg, Throwable exception)
  {
    super(msg, exception);
    wrappedException = exception;
  }

  /** Gets the chailed (wrapped) exception
   *  @return the value of wrappedException
   */
  public Throwable getWrappedException()
  {
    return wrappedException;
  }

  /** Prints the stack trace to standard err
   *  @see Throwable#printStackTrace()
   */
  public void printStackTrace()
  {
    super.printStackTrace();
    if (wrappedException != null)
    {
      System.err.println();
      System.err.print("Wrapped Exception: ");
      wrappedException.printStackTrace();
    }
  }

  /** Prints the stack trace to the specified stream
   *  @param stream the stream to print the stack trace to
   *  @see Throwable#printStackTrace(PrintStream)
   */
  public void printStackTrace(PrintStream stream)
  {
    super.printStackTrace(stream);
    if (wrappedException != null)
    {
      stream.println();
      stream.print("Wrapped Exception: ");
      wrappedException.printStackTrace(stream);
    }
  }

  /** Prints the stack trace to the specified writer
   *  @param writer the writer to print the stack trace to
   *  @see Throwable#printStackTrace(PrintWriter)
   */
  public void printStackTrace(PrintWriter writer)
  {
    super.printStackTrace(writer);
    if (wrappedException != null)
    {
      writer.println();
      writer.print("Wrapped Exception: ");
      wrappedException.printStackTrace(writer);
    }
  }

  /** This is a nested Exception, it will be included in stack traces */
  private Throwable wrappedException;
}

# devpanel
Simple library, that helps you add developer panel to your application. Here you can specify dynamic variables of your application, for example you can give tester choise api host, or api environment.

It is just alpha version yet.

You can simply add it to your project via gradle dependency:

```compile 'com.github.android-tools:devpanel:0.0.3-alpha'```

Simple to add new mutable variable for your project, that would be persisted (stored into shared preferences):

```java
private BooleanMutable mBooleanValue;
 
   @Override
   public void onCreate() {
     super.onCreate();
     initDevPanel();
  }

  protected void initDevPanel() {
    mBooleanValue = new BooleanMutable(this, "ssl_pinning", false);
    DevPanel.addMutable(mBooleanValue);
  }
  ```
  
For more info plz look at sample.

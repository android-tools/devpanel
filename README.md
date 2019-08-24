# devpanel
Simple library, that helps you add developer panel to your application. Here you can specify dynamic variables of your application, for example you can give tester choise api host, or api environment.

It is just alpha version yet.

You can simply add it to your project via gradle dependency:

```compile 'com.github.android-tools:devpanel:0.1.1-alpha'```

Simple to add new mutable variable for your project, that would be persisted (stored into shared preferences):

```java
private BooleanMutable mBooleanValue;
 
   @Override
   public void onCreate() {
     super.onCreate();
     initDevPanel();
  }

  protected void initDevPanel() {
    mBooleanValue = DevPanel.mutable()
                       .bool(false)
                       .title("Enable SSL pinning")
                       .key("ssl_pinning")
                       .add();
  }

  protected void createClient() {
    ...
    if(mBooleanValue.getData()) {
        //do something
    }
    ...
  }
```
  
For more info plz look at sample.

# ChangeLog
0.1.2-alpha:
1. Fix. Create only one shared preferences for all mutables
2. Fix. Ability to set certain preference for preference info
3. Fix. Fix attaching mutable views to category container instead of mutable container
4. Added ability to collapse categories

0.1.1-alpha:
1. Added categories. To provide you collect mutables separately group from group

0.1.0-alpha:
1. Added mutable info. You can add any mutable info read-only value for user
Toast Helper  [![Build Status](https://travis-ci.org/HuangWenhuan0/toast.svg)](https://travis-ci.org/HuangWenhuan0/toast)
===

A global toast helper util.

```java
Toaster.show("Hello, World");

Toaster.show("A button %s was clicked to say %s", button.getId(), button.getText());

Toaster.show(Toast.LENGTH_LONG, "A button %s was clicked to say %s",
                button.getId(), button.getText());
                
Toaster.show(new Throwable("throwable"), "A button %s was clicked to say",
            button.getId(), button.getText());
            
Toaster.show(Toast.LENGTH_LONG, new Throwable("throwable"), "A button %s was clicked to say",
                button.getId(), button.getText());
                
Toaster.show(R.string.display_res_id, button.getId(), button.getText());

Toaster.show(new Throwable("throwable"), 
                R.string.display_res_id, button.getId(), button.getText());
```


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

License
--------

    Copyright 2015 Huang Wenhuan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
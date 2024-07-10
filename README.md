# Fetch Android App

Android Application to fetch and display data.
## Installation

### Requirements
- Clone of the repository
- Android Studio

### Setting Up

1. Clone the repository.
2. Open project in Android Studio.
3. Run on test device/Build APK.



### File Structure
The file structure below only covers the main files in the project.
```
fetch-receipt-processor/
├─ app/src/main/                            - Main source folder for the APP
|    ├─ AndroidManifest.xml                 - App Metadata
|    ├─ java/com/example/fetchlistapp       - Contains kotlin code for the application
|      ├─ data/                             - Contains Data Classes
|         ├─ ListGroup.kt                   - Data Class for Group of ListItems
|         ├─ ListItem.kt                    - Data Class for Group of ListItem
|      ├─ ui/theme/                         - Contains code for default Theme
|      ├─ utils/                            - Contains Utility code
|         ├─ DataFetcher.kt                 - Code for connecting to internet and fetching data
|      ├─ GreetScreen.kt                    - Code for Greet Screen / Splash Screen Activity
|      ├─ ListScreen.kt                     - Code for List Screen Activity
|    ├─ res/                                - Contains resources for the application
|      ├─ layout/                           - Contains different layouts
|         ├─ activity_greet_screen.xml      - Layout for Greet Screen Activity
|         ├─ activity_list_screen.xml       - Layout for List Screen Activity
|         ├─ error_layout.xml               - Layout for displaying errors
|         ├─ list_item_layout.xml           - Layout for displaying List Item
|         ├─ sort_button_layout.xml         - Layout Sort Button
|      ├─ values/                           - Contains Global Values
|         ├─ colors.xml                     - Color Values
|         ├─ ic_launcher_background.xml     - Background color for icon
|         ├─ strings.xml                    - String Values
|         ├─ themes.xml                     - Themes Values
├─ .gitattributes                           - Attributes Specification for git
├─ README.md                                - Documentation and general information about the project
```

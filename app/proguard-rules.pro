##
# Описываем набор правил для обфускации кода:
# Правила для сторонних библиотек, находятся в директории app/proguard в виде отдельных файлов.
# Шаблоны файлов берутся из репозитория:
# https://github.com/krschultz/android-proguard-snippets/tree/master/libraries
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html
#
# author EvgenySamarin
# since 20191021 v1
##

##---------------------------##
## Общие правила, для всех проектов
-printusage build/outputs/mapping/release/usage.txt # Печатаем вырезанные библиотеки и методы
-printmapping build/outputs/mapping/release/mapping.txt # Печатаем словарь ассоциаций
-printseeds build/outputs/mapping/release/seeds.txt # Печатаем все точки входа в библиотеки

-dontobfuscate # отключаем обфускацию на время отладки, закоментируй после проверки
##===========================##

# для сохранения зависимостей уровня Dagger используется аннотация @Keep
# В текущем файле правила для классов dagger отсутствуют

# сохраняем сущности JSON неизменными
-keep class androidx.appcompat.app.ActionBarDrawerToggle { *; }
-keep class androidx.appcompat.app.ActionBarDrawerToggle$Delegate { *; }

# сохраняем библиотеку kotlinx serialization converter
-keep class com.jakewharton.retrofit2.converter.kotlinx.serialization.** { *; }


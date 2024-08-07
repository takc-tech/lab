@echo off

echo target:[%1]

if "" == "%1" (
    echo target is empty.
    goto end
)

echo --------------------------------
echo # During generation:
echo;
echo Select type of build to generate =^> 4: Basic
echo;
echo # After generation:
echo;
echo Import the generated project into Eclipse.
echo Then, set project facet to Java.
echo Project Property ^> Project Facet ^> Java
echo --------------------------------

md "%1" && ^
pushd "%1" && ^
gradle init && ^
popd

:end
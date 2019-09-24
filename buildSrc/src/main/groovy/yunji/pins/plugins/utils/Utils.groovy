package yunji.pins.plugins.utils

import org.gradle.api.Project
import org.w3c.dom.Element
import yunji.pins.plugins.bean.MicroModule
import java.io.File

import javax.xml.parsers.DocumentBuilderFactory

class Utils {

    static String upperCase(String str) {
        char[] ch = str.toCharArray()
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] -= 32
        }
        return String.valueOf(ch)
    }

    static String getAndroidManifestPackageName(File androidManifest) {
        def builderFactory = DocumentBuilderFactory.newInstance()
        builderFactory.setNamespaceAware(true)
        Element manifestXml = builderFactory.newDocumentBuilder().parse(androidManifest).documentElement
        return manifestXml.getAttribute("package")
    }

    static MicroModule buildMicroModule(Project project, String microModulePath) {


        String[] pathElements = removeTrailingColon(microModulePath).split(":")

        System.out.println("microModulePath=" + microModulePath)
        System.out.println("pathElements=" + pathElements[0])

        createFile(project, pathElements[0])

        int pathElementsLen = pathElements.size()

        File parentMicroModuleDir = project.projectDir

        for (int j = 0; j < pathElementsLen; j++) {
            parentMicroModuleDir = new File(parentMicroModuleDir, pathElements[j])
        }
        File microModuleDir = parentMicroModuleDir.canonicalFile
        String microModuleName = microModuleDir.absolutePath.replace(project.projectDir.absolutePath, "")
        if (File.separator == "\\") {
            microModuleName = microModuleName.replaceAll("\\\\", ":")
        } else {
            microModuleName = microModuleName.replaceAll("/", ":")
        }

        System.out.println("////path=" + microModuleDir.getPath())
        System.out.println("////microModuleName=" + microModuleName)

        if (!microModuleDir.exists()) {
            return null
        }

        MicroModule microModule = new MicroModule()
        microModule.name = microModuleName
        microModule.microModuleDir = microModuleDir

        return microModule
    }

    static createFile(Project project, String pinName) {

        def projectDir = project.getProjectDir().getPath()
        def files = new File("$projectDir/src/${pinName}")
        if (files.exists()) {
            printf("目录 ${pinName} 已经存在\n")
        } else {
            //指定pins-文件目录
            def applicationId = "com.yunji.pings." + pinName
            String packageDir = applicationId.replace(".", "/")
            printf("为 ${pinName} 创建目录\n")
            // 创建java目录
            new File("$projectDir/src/${pinName}/java/" + packageDir).mkdirs()
            // 创建资源文件目录
            new File("$projectDir/src/${pinName}/res/layout/").mkdirs()
            new File("$projectDir/src/${pinName}/res/values/").mkdirs()
        }
    }

    private static String removeTrailingColon(String microModulePath) {
        return microModulePath.startsWith(":") ? microModulePath.substring(1) : microModulePath
    }


}
package yunji.pins.plugins.extension

import yunji.pins.plugins.bean.MicroModule

/**
 *  Gradle Module change listener
 */
interface OnMicroModuleListener {

    void addIncludeMicroModule(MicroModule microModule, boolean mainMicroModule)

    void addExportMicroModule(String... microModulePaths)

}

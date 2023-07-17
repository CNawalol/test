import kotlinx.cinterop.*
import platform.windows.*

@OptIn(ExperimentalForeignApi::class)
fun main() {
    memScoped {
        val snapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS.toUInt(), 0U)
        if (snapshot == INVALID_HANDLE_VALUE) {
            println("Failed to create process snapshot.")
            return
        }
        val processEntry = alloc<PROCESSENTRY32>()
        processEntry.dwSize = sizeOf<PROCESSENTRY32>().convert()
        if (Process32First(snapshot, processEntry.ptr) != 0) {
            do {
                val processName = processEntry.szExeFile.toKString()
                println(processName)
            } while (Process32Next(snapshot, processEntry.ptr) != 0)
        }

        CloseHandle(snapshot)
    }
}